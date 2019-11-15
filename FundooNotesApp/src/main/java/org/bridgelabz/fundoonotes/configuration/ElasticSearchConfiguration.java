package org.bridgelabz.fundoonotes.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration

public class ElasticSearchConfiguration {
	@Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;
   
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	
	
	//To return the RestHighLevelClient object
	 @Bean(destroyMethod = "close")
	  public RestHighLevelClient  client() {
		 return new RestHighLevelClient(
			     RestClient.builder(new HttpHost("localhost",9200,"http")));
		  	 }
}
	