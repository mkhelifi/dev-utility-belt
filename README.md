# The Developer Utility Belt

This project offers a set of functionalities to make the life of programmers easier. This is the result of a constant personal quest for automation of demanding tasks that are not yet automated by popular IDEs and other tools. So, every time I face a new time demanding and repetitive task, I come in this project, drop some Clojure code there and save my day. I hope it saves your day too.

## Usage

We assume you already have JDK 8, Git and Leiningen (leiningen.org) installed and configured. Follow the steps below to use the tool:

Clone the repository locally:

    $ git clone https://github.com/EsmerilProgramming/dev-utility-belt.git

It will create the directory `dev-utility-belt` in the current location. Enter in the directory and start the REPL:

    #> cd dev-utility-belt
    #> lein repl

The REPL starts with all utility functions available by default in the namespace. You can simply type the functions or use the autocomplete (Tab) to find the one that fits your needs. Example:

    dub.core=> (replace-properties "Resources.properties"
          #_=>                     "Resources_pt.properties"
          #_=>                     "result.properties")

More functions are described in our [Wiki](https://github.com/EsmerilProgramming/dev-utility-belt/wiki).

## License

Copyright © 2015 Hildeberto Mendonca

Distributed under the Apache License V2.
