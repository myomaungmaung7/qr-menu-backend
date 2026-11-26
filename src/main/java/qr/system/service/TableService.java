package qr.system.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qr.system.entity.TableEntity;
import qr.system.exceptions.DuplicateResourceException;
import qr.system.repository.TableRepository;
import qr.system.utils.QRCodeUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    @Transactional
    public TableEntity createTableWithQRCode(String tblNumber) {
        log.info("Generating QR code for table number: {}", tblNumber);

        //Check for existing table number
        boolean exists = tableRepository.findAll().stream()
                .anyMatch(t -> t.getTableNumber().equalsIgnoreCase(tblNumber));
        if (exists) {
            throw new DuplicateResourceException("Table number '" + tblNumber + "' already existing.");
        }


        String qrData = "https://rq-menu.onrender.com/" + tblNumber;

        String qrCode = QRCodeUtil.generateQRCode(qrData, 200, 200);

        TableEntity table = new TableEntity();
        table.setTableNumber(tblNumber);
        table.setQrCode(qrCode);

        TableEntity savedTable = tableRepository.save(table);
        log.info("Table successfully created with ID: {}", savedTable.getId());
        return savedTable;
    }
}
