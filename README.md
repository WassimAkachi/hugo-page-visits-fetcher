# Hugo Page visits fetcher for the [TechEule.com](https://techeule.com/)

> All provided paths in this file are relative to the root-folder
> of this git-repository.

At [TechEule.com](https://techeule.com/) you can find more info about this repository.

## Requirements

- JDK version 17 or newer
- Maven 3.8 or newer


## How to build the application

```shell

mvn -V -B clean package

````

## How to run the application

```shell

java -Dmatomo.auth_token=4...2 \
  -Dmatomo.siteId=1234 \
  -Dmatomo.baseUri=https://matono.site.localhost \
  -Dhugo.pagesList=./hugo-list-all.csv \
  -Dhugo.dataFile=out.json \
  -jar ./target/hugo-page-visits-fetcher.jar


```