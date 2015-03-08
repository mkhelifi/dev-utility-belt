# The Java EE Utility Belt

The Java EE platform became so complex over the years that it's extremely difficult to use this technology stack without serious investments on tools, techniques and training. After all those investments, we realised there are still a lot of work to do to finally achieve faire competitiveness compared to other technologies such as Ruby, Python, Scala and Clojure.

This project aims to fullfil the gap to effective productivity on the development of Java EE projects.

Despite helping Java EE projects to succeed, this project is entirely written in Clojure. This choice was made because one cannot honestly see the problems on the Java EE platform if he/she doesn't work with a better technology. 

## Usage

We assume you already have JDK 8, Git and Leiningen (leiningen.org) installed and configured. Follow the steps below to use the tool:

Clone the repository locally:
    
    $ git clone https://github.com/htmfilho/javaee-utility-belt.git

It will create the directory `javaee-utility-belt` in the current location. Enter in the directory and start the REPL:

    #> cd javaee-utility-belt
    #> lein repl

The REPL starts with all utility functions available by default in the namespace. You can simply type the functions or use the autocomplete (Tab) to find the one that fits your needs. Example:

    javaee-utility-belt.core=> (replace-properties "Resources.properties"
                          #_=>                     "Resources_pt.properties" 
                          #_=>                     "result.properties")

More functions are described in our [Wiki](https://github.com/htmfilho/javaee-utility-belt/wiki).

## License

Copyright © 2015 Hildeberto Mendonca

Distributed under the Apache License V2.