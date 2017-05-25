/*******************************************************************************
 *
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package net.turnbig.jdbcx.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;
import net.turnbig.jdbcx.convertor.PGConversionServiceFactoryBean;

/**
 * @author Woo Cupid
 * @date 2016年1月21日
 * @version $Revision$
 */
@Component
public class Configurations {

	@org.springframework.context.annotation.Configuration
	public static class SpiedDatasourceConfig {

		@Autowired
		private DataSourceProperties properties;

		@Bean
		public DataSource dataSourceSpied() {
			DataSourceBuilder factory = DataSourceBuilder.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName()).url(this.properties.getUrl())
					.username(this.properties.getUsername()).password(this.properties.getPassword());
			return new DataSourceSpy(factory.build());
			// return factory.build();
		}
	}

	@org.springframework.context.annotation.Configuration
	public static class JdbcxConversionService {
		@Bean
		public PGConversionServiceFactoryBean jdbcxConversionService() {
			PGConversionServiceFactoryBean factoryBean = new PGConversionServiceFactoryBean();
			return factoryBean;
		}
	}

}
