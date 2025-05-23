package com.example.exception


class TransactionErrorException() :
    BusinessException(ErrorCode.TRANSACTION_ERROR)
