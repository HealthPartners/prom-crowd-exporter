# Prometheus Exporter For Crowd 

[![MIT License](https://badges.frapsoft.com/os/mit/mit.svg?v=102)](https://github.com/ellerbrock/open-source-badge/)

This is Crowd plugin which provides endpoint to expose Crowd metrics to Prometheus.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- java
- maven
- [atlassian plugin sdk](https://developer.atlassian.com/server/framework/atlassian-sdk/set-up-the-atlassian-plugin-sdk-and-build-a-project/)


### Development

Setup your local environment and produce the snogo binary.

```
$ git clone git@github.com:HealthPartners/prom-crowd-exporter.git
```

To run a local copy of crowd that already has the plugin applied, use the atlassian plugin sdk.

```
atlas-run
```

This will have the plugin already applied to the local copy of crowd.

To update the plugin with on-demand locally, in a new terminal (to allow local crowd to keep running) run:

```
atlas-mvn package
```

## Installation

1. Build the plugin using `atlas-mvn package`
1. Shut down your Crowd instance.
1. Go to CROWD-HOME/crowd-webapp/WEB-INF/lib directory, remove previous versions of the plugin (if any), and copy the downloaded JAR there.
1. Start Crowd.
1. **Prometheus Exporter For Crowd** add-on should be now correctly installed and you can access via your browser at CROWD-HOSTNAME/crowd/plugins/servlet/prometheus/metrics

## Built With

* [Java]()

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/healthpartners/prom-crowd-exporter/tags). 

## Authors

* **Peter Kreidermacher** - [HealthPartners](https://github.com/healthpartners)

See also the list of [contributors](https://github.com/healthpartners/prom-crowd-exporter/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details