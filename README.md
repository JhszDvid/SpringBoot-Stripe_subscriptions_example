# SpringBoot-Stripe_subscriptions_example
A simple Spring Boot application set up with stripe to handle monthly recurring revenues, authenticated with Keycloak

## What it does
A simple REST API to allow business owners to create businesses after subscribing and check their reviews.
Any user can post a review for any business, only business owners can see those in order to help them collect feedback.


## Steps
Create a new product on Stripe with a priceID. Get your webhook secret and stripe API keys.
Clone the repo and run keycloak, mysql with docker compose.
Finally, modify the application.yml file with your Stripe credentials.

## Enjoy!