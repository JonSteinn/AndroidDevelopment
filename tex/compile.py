import os
import signal
import subprocess

# TODO: run custom compiler with custom flags and make a .sh script

rem = [
    '/main.aux',
    '/main.lof',
    '/main.log',
    '/main.lol',
    '/main.lot',
    '/main.out',
    '/main.toc'
]
pth = os.path.abspath(".")
cmd = "pdflatex --quiet --halt-on-error main.tex"
process = subprocess.Popen(args=cmd, shell=False, stdout=subprocess.PIPE, cwd=pth)

check_return_code = True
try:
    process.wait(5)
except subprocess.TimeoutExpired:
    os.kill(process.pid, signal.SIGTERM)
    check_return_code = False

for ext in rem:
    try:
        os.remove(pth + ext)
    except FileNotFoundError:
        pass
    except PermissionError:
        pass

if check_return_code:
    if process.returncode != 0:
        file = open(pth + '/errors.log', 'w+')
        for line in process.stdout.readlines():
            file.write(bytes(line).decode(encoding='utf-8'))
        file.close()
    else:
        try:
            os.remove(pth + "/errors.log")
        except FileNotFoundError:
            pass
