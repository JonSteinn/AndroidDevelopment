import os
import subprocess
from shutil import copyfile

# re-visit ef > 9
s = "\\input{{settings.tex}}\n\\begin{{document}}\n\input{{lab0{0}/lab0{0}.tex}}\n\\end{{document}}"
labs_count = 4


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


for i in range(labs_count):
    create_main(i)
    run_py_script()
    move_pdf(i)
