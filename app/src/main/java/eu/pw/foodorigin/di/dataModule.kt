package eu.pw.foodorigin.di

import eu.pw.foodorigin.data.dataSource.BigBrandDataSource
import eu.pw.foodorigin.data.dataSource.ProductDataSource
import eu.pw.foodorigin.data.local.LocalBigBrandDataSource
import eu.pw.foodorigin.data.mapper.ProductResultDtoMapper
import eu.pw.foodorigin.data.remote.RemoteProductDataSource
import eu.pw.foodorigin.data.remote.util.HttpClientFactory
import eu.pw.foodorigin.data.remote.util.UserAgentProvider
import eu.pw.foodorigin.data.repository.BigBrandRepositoryImpl
import eu.pw.foodorigin.data.repository.ProductRepositoryImpl
import eu.pw.foodorigin.domain.repository.BigBrandRepository
import eu.pw.foodorigin.domain.repository.ProductRepository
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
	single { HttpClientFactory.create(CIO.create(), get()) }
	singleOf( ::RemoteProductDataSource).bind<ProductDataSource>()
	singleOf(::ProductRepositoryImpl).bind<ProductRepository>()
	singleOf(::LocalBigBrandDataSource).bind<BigBrandDataSource>()
	singleOf(::BigBrandRepositoryImpl).bind<BigBrandRepository>()
	single{ ProductResultDtoMapper() }
	single{ UserAgentProvider(get()) }
}