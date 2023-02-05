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

## How to configure/set up the application

### Configuration properties:

- `matomo_auth_token`: API token for the Matomo Application.
- `matomo_siteId`: The site id for which you want to generate the page views.
- `matomo_baseUri`: The base URL of the Matomo Server.
- `hugo_pagesList`: The file-path which contains the output command `hugo list all`.
- `hugo_dataFile`: The file-path in which the result (page views) are written in.

You can configure the application by using different approaches.
The application can be configured via `properties`file, JVM-Options or
environment variables. Resolution from the highest priority to
the lowes-filet (fallback):

1. Environment variables
2. JVM-Options
3. Configuration `.properties`-file (`--config`)

Configuration-properties file:

````properties
# hugo-page-views-config.properties
matomo_auth_token=<the auth token>
matomo_siteId=<your site id>
matomo_baseUri=<base url for the matomo server>
hugo_pagesList=<hugo output-file from command 'hugo list all'>
hugo_dataFile=<the result file in which to save the page views>
````

## How to run the application

### start with only `--config` properties file

```shell
# no spaces before and after the '=' in the ' --config=<Path-to-config-file>'
java  -jar ./target/hugo-page-visits-fetcher.jar --config=<Path-to-config-file like hugo-page-views-config.properties>

```

### start with `--config` properties file and JVM-Options

```shell
# no spaces before and after the '=' in the ' --config=<Path-to-config-file>'
java -Dmatomo_siteId=1234  -jar ./target/hugo-page-visits-fetcher.jar --config=<Path-to-config-file like hugo-page-views-config.properties>

```

### start with `--config` properties file, JVM-Options and environment variable(s)

```shell
# environment variable
matomo_auth_token=very-secret-value....

# no spaces before and after the '=' in the ' --config=<Path-to-config-file>'
java -Dmatomo_siteId=1234  -jar ./target/hugo-page-visits-fetcher.jar --config=<Path-to-config-file like hugo-page-views-config.properties>

```