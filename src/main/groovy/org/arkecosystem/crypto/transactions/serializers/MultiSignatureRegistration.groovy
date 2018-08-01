package org.arkecosystem.crypto.transactions.serializers

import org.arkecosystem.crypto.transactions.Transaction
import org.arkecosystem.crypto.helpers.Base58
import java.nio.ByteBuffer
import static com.google.common.io.BaseEncoding.base16

class MultiSignatureRegistration extends AbstractSerializer {
    MultiSignatureRegistration(ByteBuffer buffer, Transaction transaction) {
        super(buffer, transaction)
    }

    void serialize() {
        List keysgroup = []

        if (!this.transaction.version) {
            for (int i = 0; i < this.transaction.asset.multisignature.keysgroup.size(); i++) {
                def element = this.transaction.asset.multisignature.keysgroup[i]

                if ('+' == element.substring(0, 1)) {
                    keysgroup[i] = element.substring(1)
                } else {
                    keysgroup[i] = element
                }
            }
        } else {
            keysgroup = this.transaction.asset.multisignature.keysgroup
        }

        this.buffer.put this.transaction.asset.multisignature.min.byteValue()
        this.buffer.put this.transaction.asset.multisignature.keysgroup.size().byteValue()
        this.buffer.put this.transaction.asset.multisignature.lifetime.byteValue()
        this.buffer.put base16().lowerCase().decode(keysgroup.join(""))
    }
}
