#!/usr/bin/python3

import subprocess
from flask import Flask, Response
import threading
from multiprocessing import Process
import os

app = Flask(__name__)

COMMAND_START_DOCKER    = ['docker-compose', 'up']
COMMAND_STOP_DOCKER     = ['docker-compose', 'stop']
COMMAND_RESTART_DOCKER  = ['docker-compose', 'restart']

def start_docker():
    subprocess.call(COMMAND_START_DOCKER)

def stop_docker():
    subprocess.call(COMMAND_STOP_DOCKER)

def restart_docker():
    subprocess.call(COMMAND_RESTART_DOCKER)

def root_dir():  # pragma: no cover
    return os.path.abspath(os.path.dirname(__file__))

def get_file(filename):  # pragma: no cover
    try:
        src = os.path.join(root_dir(), filename)
        return open(src).read()
    except IOError as exc:
        return str(exc)


@app.route('/')
def homepage():
    content = get_file('dashboard.html')
    return Response(content, mimetype="text/html")

@app.route('/start')
def start():
    p = Process(target=start_docker)
    p.start()
    p.join()
    return "Server started"

@app.route('/stop')
def stop():
    p = Process(target=stop_docker)
    p.start()
    p.join()
    return 'Server is stopped'

@app.route('/restart')
def restart():
    p = Process(target=restart_docker)
    p.start()
    p.join()
    return 'Server restarted!'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8880)
