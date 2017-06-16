# Goal

Make the service like `DockerHub`.

# TODO

- [done] 6/14 Set up gitbucket and jenkins, docker-registry with docker.
- [done] 6/14 Set up PlayFramework.
- [done] 6/14 Set up VueJs.
- [done] 6/15 Set up a docker engine to be used by Jenkins.
- [done] 6/15 Create test repo(gitbucket), job(jenkins).
- Understand how to use build with Jenkin's API.
    - Create jenkins-client.
- Understand how to access repositories with Gitbucket API.
    - Create gitbucket-client.
- Implement API server.
    - User authentication
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