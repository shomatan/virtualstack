# VirtualStack

`VirtualStack` is a simple Docker registry, to enable automated build with jenkins and gitbucket.

This application is used to showcase the Playframework as well as Vue.js. This application makes use of the following:

- [Playframework 2.5](http://www.playframework.com)
  - [Javascript Routing](https://www.playframework.com/documentation/2.5.x/ScalaJavascriptRouting)
  - [JSON Automated Mapping](https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated)
  - [Scala Forms](https://www.playframework.com/documentation/2.5.x/ScalaForms)
- [Vue.js](https://vuejs.org/)
  - [Vue Router](http://router.vuejs.org/en/index.html)
  - [axios](https://github.com/mzabriskie/axios)
- [Node.js](https://nodejs.org/en)
- [Webpack](https://webpack.github.io)
- [Bootstrap](http://www.bootstrap.com) 
- [Slick](http://www.slick.typesafe.com)


## Development

### Requirements

- sbt
- nodejs
- docker

### Setup

    docker-compose up -d
    sbt "~run"
    webpack --watch


- Virtual Stack [http://localhost:8080]
- GitBucket [http://localhost:8081]
- Jenkins [http://localhost:8082]
- Docker registry [http://localhost:5000]