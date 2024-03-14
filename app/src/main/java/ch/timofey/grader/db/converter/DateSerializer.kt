package ch.timofey.grader.db.converter

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

object DateSerializer: KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.ofEpochDay(decoder.decodeLong())
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeLong(value.toEpochDay())
    }
}