# Testing Starter for Java
[![Build Status](https://travis-ci.org/wavesoftware/java-testing-starter.svg?branch=develop)](https://travis-ci.org/wavesoftware/java-testing-starter) [![Build status](https://ci.appveyor.com/api/projects/status/381g89l6entvp0nk/branch/develop?svg=true)](https://ci.appveyor.com/project/cardil/java-testing-starter/branch/develop) [![Quality Gate](https://sonar.wavesoftware.pl/api/badges/gate?key=pl.wavesoftware.testing:testing-starter-parent)](https://sonar.wavesoftware.pl/dashboard/index/pl.wavesoftware.testing:testing-starter-parent) [![Technical Dept](https://sonar.wavesoftware.pl/api/badges/measure?key=pl.wavesoftware.testing%3Atesting-starter-parent&metric=sqale_debt_ratio)](https://sonar.wavesoftware.pl/dashboard/index/pl.wavesoftware.testing:testing-starter-parent) [![codecov](https://codecov.io/gh/wavesoftware/java-testing-starter/branch/develop/graph/badge.svg)](https://codecov.io/gh/wavesoftware/java-testing-starter) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.wavesoftware.testing/junit5-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/pl.wavesoftware.testing/junit5-starter)

A simple testing starter for Java projects

## Installation

```xml
<dependency>
  <groupId>pl.wavesoftware.testing</groupId>
  <artifactId>junit5-starter</artifactId>
  <version>1.0.0</version>
  <packaging>pom</packaging>
  <scope>test</scope>
</dependency>
```

## Usage

Simply adding a dependency should do the trick. There will be:

 * logging configured with log4j2, slf4j, jul and jcl
 * proper logging integration with Spring/Spring-Boot, if present, and `EnableAutoConfiguration` 
 is used.
 * color logs
 * logging collector for log4j2 for testing purposes

## Contributing

Contributions are welcome!

To contribute, follow the standard [git flow](http://danielkummer.github.io/git-flow-cheatsheet/) of:

1. Fork it
1. Create your feature branch (`git checkout -b feature/my-new-feature`)
1. Commit your changes (`git commit -am 'Add some feature'`)
1. Push to the branch (`git push origin feature/my-new-feature`)
1. Create new Pull Request

Even if you can't contribute code, if you have an idea for an improvement please open an [issue](https://github.com/wavesoftware/java-testing-starter/issues).

## Requirements

* Java 8

## Releases

* `1.0.0` - codename: *FrostyHeart*
	* First publicly available release
