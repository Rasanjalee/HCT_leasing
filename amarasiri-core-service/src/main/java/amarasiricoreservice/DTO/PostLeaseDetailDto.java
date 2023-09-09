package amarasiricoreservice.DTO;

import amarasiricoreservice.entity.LeaseMaster;
import amarasiricoreservice.entity.LeaseeMaster;
import amarasiricoreservice.entity.Vehicle;
import lombok.Data;

@Data
public class PostLeaseDetailDto {

    private LeaseeMaster leaseeMaster;
    private LeaseMaster leaseMaster;
    private Vehicle vehicle;
}
