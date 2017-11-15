import os
import subprocess
from shutil import copyfile

# re-visit ef > 9
s = "\\input{{settings.tex}}\n\\begin{{document}}\n\input{{lab0{0}/lab0{0}.tex}}\n\\end{{document}}"
labs_count = 7


def clean_old(lab):
    try:
        os.remove("../pdf/lab0{0}.pdf".format(lab))
    except FileNotFoundError:
        pass


def create_main(lab):
    fd = open("main.tex", "w")
    fd.write(s.format(lab))
    fd.close()


def run_py_script():
    pth = os.path.abspath(".")
    cmd = "python compile.py"
    process = subprocess.Popen(args=cmd, shell=False, stdout=subprocess.PIPE, cwd=pth)
    process.wait()


def move_pdf(lab):
    if os.path.isfile("main.pdf"):
        copyfile("main.pdf", "../pdf/lab0{0}.pdf".format(lab))
        os.remove("main.pdf")


directory = os.path.dirname('../pdf/')
if not os.path.exists(directory):
    os.makedirs(directory)
for i in range(labs_count):
    clean_old(i)
    create_main(i)
    run_py_script()
    move_pdf(i)
