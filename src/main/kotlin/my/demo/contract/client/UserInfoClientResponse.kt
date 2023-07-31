package my.demo.contract.client

data class UserInfoClientResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumbers: List<PhoneNumber>
)

data class PhoneNumber(val type: PhoneType, val number: String)

enum class PhoneType {
    MOBILE,
    HOME,
    WORK
}
