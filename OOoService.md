# Introduction #

This page will explain how to run OpenOffice.org as a service.  Explainations on how to run it as a background service will be added later.

# Details #

**Make sure that you have X11 on your disk**

**Install OpenOffice 2.3 (should work with any 2.0 releases)**

**Start X11**

**In a xterm window, start this command:**

/Applications/OpenOffice.org\ 2.3.app/Contents/MacOS/soffice "-accept=socket,host=localhost,port=8100;urp" &

This command will start OpenOffice.org as a service running on port 8100 on localhost.