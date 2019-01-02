package run.controller;

import org.springframework.stereotype.Service;

@Service
public class FeignForETCRFailure implements FeignForETCR {
    @Override
    public String deleteETCRBySite(String site_id) {
        return null;
    }

    @Override
    public String deleteETCRByRTU(String rtu_id) {
        return null;
    }

    @Override
    public int selectETCRCountBySite(int site_id) {
        System.out.println("etcr service is not available !");
        return 0;
    }
}
