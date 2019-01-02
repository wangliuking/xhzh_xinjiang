package run.controller;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import run.feign.FeignConf;

@FeignClient(name="service-etcr",configuration= FeignConf.class,fallback = FeignForETCRFailure.class)
public interface FeignForETCR {

    @RequestLine("GET /deleteETCRBySite?site_id={site_id}")
    @Headers("Content-Type: application/json")
    public String deleteETCRBySite(@Param("site_id") String site_id);

    @RequestLine("GET /deleteETCRByRTU?rtu_id={rtu_id}")
    @Headers("Content-Type: application/json")
    public String deleteETCRByRTU(@Param("rtu_id") String rtu_id);

    @RequestLine("GET /selectETCRCountBySiteId?site_id={site_id}")
    @Headers("Content-Type: application/json")
    public int selectETCRCountBySite(@Param("site_id") int site_id);
}
