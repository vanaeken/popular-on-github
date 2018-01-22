POPULAR-ON-GITHUB

This is the source code for popular-on-github, a service that assigns a popularity score to GitHub repositories.

The GitHub URL is https://github.com/vanaeken/popular-on-github

The project can be cloned from https://github.com/vanaeken/popular-on-github.git

POPULARITY

Popularity is calculated by adding the number of GitHub stars and the number of git@github.com/{owner}/{name}.git references on the Web.

BUILDING, TESTING, RUNNING, EXPERIMENTING

mvn package
java -jar target/popular-on-github-0.0.1-SNAPSHOT.jar

Once the service is running, the interactive documentation is available at http://localhost:8080/swagger-ui.html

In the documentation, the core controller is popularity-controller.

TODO

The definition of 'popularity' needs to be clarified with the customer.
Are we focusing on use (# clones), or contributions (# committers, etc.), or still something else?

The behavior of Google Custom Search is not very clear. We need to understand the service better.

In general, we need to evaluate if Google Custom Search is a good choice for a secondary popularity source.

We probably want to update the consolidation mechanism, which is currently very simple.
Here also, we need to consult with the customer.

Error handling, and messaging, needs to be extended.

In general, we must make the application more robust.

The mechanism to ignore the secondary source if unavailable needs improving.
We might want to use a circuit-breaker.

We need to parallelize outgoing calls as much as possible.

In general, we need to load-test, profile and performance-optimize the application.

If requirements allow, we might want to use caching.

Secrets need to dealt with properly, e.g. by using a vault.

Test coverage can still be improved a bit.

We need to make integration testing more robust.
We probably want to create our own test data (repositories) on GitHub.

Documentation can still be improved.

The code needs to be reviewed.

NOTES TO SELF

In the GitHub GraphQL schema, the 'repository' definition starts at line 3229 (see https://api.github.com/graphql)

