# Adopt Kotlin as the Primary Programming Language

- Status: Accepted
- Deciders: Daniel
- Date: 2023-06-11
- Technical Story: Project setup

## Context and Problem Statement

For the development of our new project, we are presented with the question of: "Which programming language should be used?" This decision involves considering the availability of
resources, language capabilities, scalability, and maintainability.

## Considered Options

- **Kotlin**
- Java

## Decision Drivers

- **Familiarity:** Familiarity of the team with the language.
- **Application Nature:** The appropriateness of the language for the project requirements.
- **Community Support:** The extent of community development support and resources available for problem-solving.
- **Tooling:** Availability of reliable tools for development, testing, and deployment processes.
- **Performance:** The runtime performance of the language.

## Decision Outcome

Chosen option: "Kotlin", because it meets most of the requirements and needs addressed by the decision drivers. Here are the reasons:

- Kotlin is a statically-typed language, meaning it is expected to perform better than dynamically-typed languages.
- The team is already familiar with Kotlin, which means we can hit the ground running.
- Kotlin is very much suitable for Android development and backend development with Spring, both of which are important for our project.
- Strong tooling support with IntelliJ IDEA, a robust IDE which also supports Android development environment (Android Studio is based on IntelliJ IDEA).
- Kotlin is interoperable with Java which gives access to a vast number of libraries.

## Consequences

Assuming that Kotlin will be adopted as the primary programming language, there will be consequences that we can anticipate:

**Positive Consequences**

- The development speed is expected to be faster due to language simplicity and conciseness.
- Kotlin has null-safety built into the language which helps prevent NullPointer exceptions.
- Better maintainability due to simpler and more readable code.

**Negative Consequences**

- There could be less experienced contractors or new hires that aren't familiar with Kotlin, which may demand additional resources for training.
- Despite being interoperable with Java, there might be some libraries or frameworks which are not fully supported in Kotlin.
