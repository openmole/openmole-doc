# openmole-site

## Build
The project is separated into two parts: a JVM and an JS one. The page locations have to be generated from scalatex and then built for the js part thanks to the macrosite project.

To do so, run:
```sh
buildMacro
```
It will add a ```maropackage.scala``` file in your js project. Build this macro each time your scalatex structure changes so that it is available through the js part

Then, build the whole site with:
```sh
buildSite --target the/path/of/your/website
```
