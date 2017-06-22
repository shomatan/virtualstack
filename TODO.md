# Goal

Make the service like `DockerHub`.

# TODO

- [done] 6/14 Set up gitbucket and jenkins, docker-registry with docker.
- [done] 6/14 Set up PlayFramework.
- [done] 6/14 Set up Vue.js.
- [done] 6/15 Set up a docker engine to be used by Jenkins.
- [done] 6/15 Create test repo(gitbucket), job(jenkins).
- [done] 6/17 Set up postgreSQL for virtualstack app.
- [done] 6/18 Write document (README.md)
- Understand how to use build with Jenkin's API.
    - Create jenkins-client.
- Understand how to access repositories with Gitbucket API.
    - [WIP] Create gitbucket-client.
- Implement API server.
    - [WIP]User authentication
        - [done] 6/22 JWT auth controller
        - Vue.js JWT auth
    - [WIP] Modeling 
    - [WIP] Link to `docker-registry`
- Customize ready-to-use docker image for each service. 
- Test gitbucket's webhook.
- Link applications.
    - Build image from VirtualStack.

- Corresponding to other storage.(Default `local`)
    - AWS S3
    - Openstack
    - and more...