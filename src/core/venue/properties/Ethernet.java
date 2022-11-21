package core.venue.properties;

import java.util.LinkedHashMap;
import java.util.Map;

public class Ethernet {

    // instance variables
    private String ipAddress, subnetMask, defaultGateway, preferredDNS, alternateDNS;

    // constructor
    private Ethernet(Builder builder) {
        this.ipAddress = builder.ipAddress;
        this.subnetMask = builder.subnetMask;
        this.defaultGateway = builder.defaultGateway;
        this.preferredDNS = builder.preferredDNS;
        this.alternateDNS = builder.alternateDNS;
    }

    // method to get the ethernet details
    public Map<String, String> getEthernetDetails(){
        return new LinkedHashMap<String, String>(){
            {
                put("IP Address", ipAddress);
                put("Subnet Mask", subnetMask);
                put("Default Gateway", defaultGateway);
                put("Preferred DNS", preferredDNS);
                put("Alternate DNS", alternateDNS);
            }
        };
    }

    // Builder
    public static class Builder {
        private String ipAddress, subnetMask, defaultGateway, preferredDNS, alternateDNS;

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder subnetMask(String subnetMask) {
            this.subnetMask = subnetMask;
            return this;
        }

        public Builder defaultGateway(String defaultGateway) {
            this.defaultGateway = defaultGateway;
            return this;
        }

        public Builder preferredDNS(String preferredDNS) {
            this.preferredDNS = preferredDNS;
            return this;
        }

        public Builder alternateDNS(String alternateDNS) {
            this.alternateDNS = alternateDNS;
            return this;
        }

        public Ethernet build() {
            return new Ethernet(this);
        }
    }
}