# The Java EE Utility Belt

The Java EE platform became so complex over the years that it's extremelly difficult to use this technology stack without serious investiments on tools, techniques and training. After all those investiments, we realised there are still a lot of work to do to finally achieve faire compentitiviness compared to other technologies such as Ruby, Python, Scala and Clojure.

This project aims to fullfil the gap to effective productivity on the development of Java EE projects.

Despite helping Java EE projects to succeed, this project is entirelly written in Clojure. This choice was made because one cannot honestly see the problems on the Java EE platform if he/she doesn't work with a better tecnology. 

## Installation

This project is just starting. So, there is no installation package at the moment. The user should be capable of cloning the project and running it from the source code.

## Usage

We assume you already have JDK 8, Git and Leiningen installed and configured. We also assume you have basic knowledge about the Clojure programming language to write commands syntactically correct in the REPL. Follow the steps below to use the tool:

Clone the repository locally:
    
    #> git clone https://github.com/htmfilho/javaee-utility-belt.git

It will create the directory `javaee-utility-belt` in the current location. Enter in the directory and start the REPL:

    #> cd javaee-utility-belt
    #> lein repl

The REPL starts with all utility functions available by default in the namespace. You can simply type the functions or use the autocomplete (Tab) to find the one that fits your needs. Example:

    javaee-utility-belt.core=> (replace-properties "Resources.properties"
                          #_=>                     "Resources_pt.properties" 
                          #_=>                     "result.properties")

The function `replace-properties` replaces the properties of a property file by the properties of another property file and saves the result in another property file. In the example above, the files are in the same directory where the REPL started. If the files are in another directory, you have to inform the complete path to them. Note that you can break the command in several lines while the expression is not complete, in this case, while the closing parentheses is not there yet. When you finally put the closing parentheses in the last line and hit Enter the function will execute. The file `result.properties` is created in the current directory.

## License

Copyright © 2015 Hildeberto Mendonca

Distributed under the Apache License V2.