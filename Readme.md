Infrastrcture as (scala)code.

### Getting started

Describe your infra

`aws.scala`

```scala
import surya.wow.aws.AWS

val one = AWS.instance(instanceType = "t2.micro", ami = "ami-2757f631")
val eip = AWS.eip(one)

```
` build.sbt` (Not needed but you can you use ide, code completion ect )
```
scalaVersion := "2.11.8"
libraryDependencies += "surya" % "wow" % "0.1.0-SNAPSHOT"
```

`wow.properties`

```
[scala]
  version: 2.11.8

[app]
  org: surya
  name: wow
  version: 0.1.0-SNAPSHOT
  class: surya.wow.Main
  components: xsbti
  cross-versioned: false

[repositories]
  local
  maven-central

```

Create your infra

``` 
sbt @wow.properties "create aws.scala"
...
Resource Instance(ami-2757f631,t2.micro) exists. Skipping..                            
Resource EIP(Instance(ami-2757f631,t2.micro)) exists. Skipping.. 
```