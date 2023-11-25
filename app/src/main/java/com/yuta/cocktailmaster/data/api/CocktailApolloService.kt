package com.yuta.cocktailmaster.data.api

import com.apollographql.apollo3.ApolloClient

object CocktailApollo {
    val apolloClient: ApolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:4000/graphql")
            .build()
    }
}
