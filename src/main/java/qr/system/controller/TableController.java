package qr.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qr.system.entity.TableEntity;
import qr.system.response.ApiResponse;
import qr.system.service.TableService;
import qr.system.utils.ResponseUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/table")
public class TableController {

    private final TableService tableService;

    @PostMapping("/qr-generate")
    public ResponseEntity<ApiResponse<TableEntity>> generateTableQr(@RequestParam String tblNumber) {
        TableEntity savedTable = tableService.createTableWithQRCode(tblNumber);
        return ResponseUtil.created("Table and QR Code generated successfully.", savedTable);
    }
}
