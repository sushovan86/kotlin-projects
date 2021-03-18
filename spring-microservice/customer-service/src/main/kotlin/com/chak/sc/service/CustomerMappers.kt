package com.chak.sc.service

import com.chak.sc.entity.Address
import com.chak.sc.entity.Customer
import com.chak.sc.model.AddressDTO
import com.chak.sc.model.CustomerDTO
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface AddressMapper {

    fun toAddressDTO(address: Address): AddressDTO

    @InheritInverseConfiguration
    fun toAddress(addressDTO: AddressDTO): Address
}

@Mapper(componentModel = "spring", uses = [AddressMapper::class])
interface CustomerMapper {

    @Mappings(
        Mapping(source = "dob", target = "dataOfBirth"),
        Mapping(source = "customer.addressList", target = "addresses")
    )
    fun toCustomerDTO(customer: Customer): CustomerDTO

    @InheritInverseConfiguration
    fun toCustomer(customerDTO: CustomerDTO): Customer
}