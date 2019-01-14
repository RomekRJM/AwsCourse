import argparse
import time
from threading import Lock, Thread

import matplotlib.pyplot as plt
import plotly.plotly as py
import requests
from requests import ConnectionError

"""
You need to sign up for a plot.ly account and set up ~/.plotly/.credentials in order to use interactive mode
"""

LOCK = Lock()
TOTAL_CALLS = 0
REPORT = True
CALLS = {
    'x': [],
    'y': []
}


def monitor(started_at):
    total_time = 0
    interval = 1
    stop = 2

    while stop:
        LOCK.acquire()
        total_calls = TOTAL_CALLS
        LOCK.release()

        call_time = get_current_time_millis() - started_at
        requests_per_second = (total_calls / float(call_time)) * 1000
        CALLS['x'].append(call_time)
        CALLS['y'].append(requests_per_second)

        if total_calls > 0:
            print "Called endpoint " + str(total_calls) + " times at a rate: " \
                  + str(requests_per_second) + " req/s"
        else:
            print "Imma chargin mah lazor..."

        time.sleep(interval)
        total_time += 1000 * interval

        if not REPORT:
            stop -= 1


class Lazor:

    def __init__(self, url):
        self.url = url

    def beam(self):
        try:
            requests.get(self.url)
            self.atomic_increment()
        except ConnectionError as ce:
            print "Can't connect to the service"

    def atomic_increment(self):
        global TOTAL_CALLS
        LOCK.acquire()
        TOTAL_CALLS += 1
        LOCK.release()


def generate_plot(interactive=True, png=False):
    fig, a1x = plt.subplots(sharex=True)
    a1x.plot(CALLS['x'], CALLS['y'])

    a1x.set(xlabel='time (s)', ylabel='No. requests', title='LOIC speed (total requests: {})'.format(TOTAL_CALLS))
    a1x.grid()

    fig.savefig("test.png")

    if interactive:
        py.plot_mpl(fig)

    if png:
        plt.show()


def get_current_time_millis():
    return int(round(time.time() * 1000))


def imma_chargin_mah_lazor(url, call_me_on, started_at, duration_in_millis, results):
    lazor = Lazor(url)

    for next_call in call_me_on:
        now = get_current_time_millis()
        time_since_test_started = now - started_at
        time_until_next_call = next_call - time_since_test_started

        if time_until_next_call > 0:
            time.sleep(time_until_next_call / 1000.0)

        lazor.beam()

        if time_since_test_started >= duration_in_millis:
            break


def split_into_task(call_times, task_no, no_threads):
    splitted = []

    for i in range(len(call_times)):
        ms = call_times[i]
        if i % no_threads == task_no:
            splitted.append(ms)

    return splitted


def low_orbit_ion_cannon(url, no_threads, duration_in_millis):
    global REPORT
    call_times = [x for x in range(0, duration_in_millis, 1000/no_threads)]
    threads = [None] * no_threads
    results = [None] * no_threads

    started_at = get_current_time_millis()

    for thread_no in range(0, no_threads):
        call_times_for_task = split_into_task(call_times, thread_no, no_threads)
        threads[thread_no] = Thread(target=imma_chargin_mah_lazor,
                                    args=(url, call_times_for_task, started_at, duration_in_millis, results))
        threads[thread_no].start()

    monitor_thread = Thread(target=monitor, args=(started_at,))
    monitor_thread.start()

    for thread_no in range(0, no_threads):
        threads[thread_no].join()

    REPORT = False
    monitor_thread.join()

    generate_plot()


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-t', '--threads', help='number of threads, corresponds to number of calls per second', required=True)
    parser.add_argument('-d', '--duration', help='how long test should take in milliseconds', required=True)
    parser.add_argument('-u', '--url', help='url to call', required=True)
    args = parser.parse_args()

    low_orbit_ion_cannon(args.url, int(args.threads), int(args.duration))