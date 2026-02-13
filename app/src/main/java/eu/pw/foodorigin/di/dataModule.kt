package eu.pw.foodorigin.di

import eu.pw.foodorigin.data.HttpClientFactory
import eu.pw.foodorigin.data.dataSource.ProductDataSource
import eu.pw.foodorigin.data.remote.RemoteProductDataSource
import eu.pw.foodorigin.data.repository.ProductRepositoryImpl
import eu.pw.foodorigin.domain.repository.ProductRepository
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
	single { HttpClientFactory.create(CIO.create()) }
	singleOf( ::RemoteProductDataSource).bind<ProductDataSource>()
	singleOf(::ProductRepositoryImpl).bind<ProductRepository>()
}