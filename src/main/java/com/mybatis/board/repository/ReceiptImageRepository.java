package com.mybatis.board.repository;

import com.mybatis.board.vo.ReceiptImageVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReceiptImageRepository {
    private final SqlSessionTemplate sqlSessionTemplate;

    public void save(ReceiptImageVO receiptImageVO) {
        sqlSessionTemplate.insert("ReceiptImage.saveReceiptImage", receiptImageVO);
    }
}
