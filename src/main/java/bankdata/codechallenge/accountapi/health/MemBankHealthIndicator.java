package bankdata.codechallenge.accountapi.health;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * MemBank might have problems if running out of memory, which unfortunately also means every account will loose their status..
 * Refer to https://www.baeldung.com/java-metrics for more POI  
 */
@Component
public class MemBankHealthIndicator implements HealthIndicator {
	
	private static final Logger LOG = LogManager.getLogger();
	private long GB = 1073741824L;
	
	/**
	 * 
	 */
    @Override
    public Health health() {
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	
        if (((double)memoryMXBean.getHeapMemoryUsage().getUsed()) / (double)memoryMXBean.getHeapMemoryUsage().getMax() > 0.8) {
        	LOG.warn(
        		"HEALTH is CRITICAL {}, {}", 
        		String.format("Initial memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getInit() / GB) ,
        		String.format("Max heap memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getMax() / GB)
        	);
        	return Health.down().build();
        }
        
        LOG.info(
        		"HEALTH is OK {}, {}", 
        		String.format("Initial memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getInit() / GB) ,
        		String.format("Max heap memory: %.2f GB", (double)memoryMXBean.getHeapMemoryUsage().getMax() / GB)
        	);
        
        return Health.up().build();
    }
}