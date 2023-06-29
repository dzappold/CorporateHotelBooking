# Monolithic Repository

- Status: accepted
- Deciders: Daniel
- Date: 2023-06-10
- Technical Story: project setup

## Context and Problem Statement

How to organize a project with multiple services? Should they stay in different repositories or should the be in same
repository? Another option might be to create a modularized monolith

## Considered Options:

- [Monorepo](#monorepo)
- [repo per service and shared code as library](#repo-per-service-and-shared-code-as-library)
- [Monolith instead of multiple services](#monolith-instead-of-multiple-services)

## Decision Outcome:

Chosen option: "Monorepository",
because I see some advantages and opportunities while testing and using synergies in a quite easy way.

## Consequences:

- Positive Consequences
    - whole project stays in one single repository
    - updates are easy, since dependencies are defined only once
    - easy to build end-2-end test, since it is simple composing functions
    - easy to share common code
- Negative Consequences
    - project configuration might be challenging
    - can become huge
    - upgrades with breaking changes can be challenging

## Open Questions -> Remarks:

- what's the best way to organize gradle with multiple subprojects?

## Pros and Cons of the Options:

### Monorepo

Monorepos are used by a lot of companies, since it makes it easy to organize a whole product / customer service in such
a way.

- Good, because everything stays together
- Good, because it's easy to update dependencies all at once
- Good, because it's easy to share some common code
- Good, because it's easy to compose end-2-end tests
- Bad, because it can become huge (which requires a good structure and discipline)
- Bad, because project configuration might be challenging

### repo per service and shared code as library

Another wildly used pattern to structure larger projects.

- Good, because each service is separated
- Good, because it's easy to change structure of a single service
- Good, because hand over to another team is quite easy
- Bad, because it's easy to lose overview of all required repositories
- Bad, because it's requires more effort to keep all services up-to-date
- Neutral, it requires some kind of contract testing

### Monolith instead of multiple services

Might be an option (which can also fulfilled by [Monorepo](#monorepo)) when it's not required to make independent
deployments and not using individual scaling.

- Good, because it's easy to deploy (deployment monolith)
- Good, because makes refactorings easy
- Good, because it's easy to use shared code
- Bad, because requires discipline to keep modularity
