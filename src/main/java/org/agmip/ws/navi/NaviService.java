package org.agmip.ws.navi;

import org.agmip.ws.navi.config.NaviConfig;
import org.agmip.ws.navi.db.NaviDAO;
import org.agmip.ws.navi.resources.NaviResource;
import org.skife.jdbi.v2.DBI;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;

public class NaviService extends Service<NaviConfig>{
	public static void main(String[] args) throws Exception {
		new NaviService().run(args);
	}
	
	@Override
	public void initialize(Bootstrap<NaviConfig> bootstrap) {
		bootstrap.setName("navi");
	}
	
	@Override
	public void run(NaviConfig config, Environment env) throws ClassNotFoundException {
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(env, config.getDatabaseConfiguration(), "postgresql");
		final NaviDAO dao = jdbi.onDemand(NaviDAO.class);
		env.addResource(new NaviResource(dao));
	}
}
