# params-cleanup-utility
This is used for finding out the parameters that are not in use. It periodically hit the param cache and retrieve the params that are in use at that point in time
and persist it into the db. 

#Database and table requirements:

CREATE DATABASE scalatestdb1;
CREATE TABLE `parameter` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4083796 DEFAULT CHARSET=utf8;

#How to run the play application:

Make sure you are on VPN

Port-forward steps:
1)switch context to prod1 for blue & prod2 for green
2)kubectl get pods --namespace blue
3)Find out the pod named "param....." and use that in the below.

kubectl port-forward api-param-service-69db8995d7-64rzt 10000:9000 --namespace=blue

/Users/jptst/myExperements/param-service-client/keep-alive.sh (script for live check)


cd params-cleanup-utility
mysbt
run (To run it in default port 9000)
localhost:9000 (TO bootstrap the application)
