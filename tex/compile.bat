where /q python
IF ERRORLEVEL 1 (
    ECHO Python is required!
    set /p DUMMY=press any key to quit
) ELSE (
    where /q pdflatex
    IF ERRORLEVEL 1 (
        ECHO pdflatex is required!
        set /p DUMMY=press any key to quit
    ) ELSE (
        py compile.py
    )
)