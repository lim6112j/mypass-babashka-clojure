#!/bin/bash
bb uberscript src/mypass/app -m mypass.app
mkdir -p out
awk 'BEGIN{print "#!/usr/bin/env bb\n"} {print}' src/mypass/app > out/mypass
chmod +x out/mypass
