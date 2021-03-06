package info.tommarsh.eventsearch.core.data.attractions.remote

import okhttp3.Interceptor
import okhttp3.Response

class AttractionsAPIInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url()
            .newBuilder()
            .addQueryParameter("apikey", "XpumtmXUMaZMTgnVA2UGNQ88okEFHMOk")
            .build()

        val request = chain.request().newBuilder().url(url).build()

        return chain.proceed(request)
    }
}