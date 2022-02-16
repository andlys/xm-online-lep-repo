package function

import com.icthh.xm.ms.entity.domain.Attachment
import com.icthh.xm.ms.entity.domain.Content
import com.icthh.xm.ms.entity.domain.XmEntity
import com.icthh.xm.ms.entity.domain.ext.IdOrKey

import java.time.Instant

return new Function_Copy_Attachments(lepContext).invoke()

class Function_Copy_Attachments {
    def attachmentService;
    def entityService;
    def inputData;
    def attachmentIds;
    def destinationEntityId;

    Function_Copy_Attachments(Map<String, Object> lepContext) {
        this.attachmentService = lepContext.services.attachmentService
        this.entityService = lepContext.services.xmEntity
        this.inputData = lepContext.inArgs.functionInput
        this.attachmentIds = inputData.attachmentIds
        this.destinationEntityId = inputData.destinationEntityId
    }

    def invoke() {
        def response = []
        attachmentIds.each({
            id ->
                Attachment attachment = attachmentService.getOneWithContent(id).get()


                XmEntity entity = entityService.findOne(IdOrKey.of(destinationEntityId))
                def copiedAttachment = copyAttachment(attachment, entity)
                attachmentService.save(copiedAttachment)
                response.add([
                        attachmentId: copiedAttachment.getId(),
                        contentId   : copiedAttachment.content.id,
                ])
        })

        return [attachments: response]
    }

    def copyAttachment(Attachment attachment, XmEntity destinationEntity) {
        def copiedAttachment = new Attachment()
        copiedAttachment.with {
            typeKey = attachment.typeKey
            name = attachment.name
            contentUrl = attachment.contentUrl
            startDate = Instant.now()
            valueContentType = attachment.valueContentType
            valueContentSize = (long) attachment.valueContentSize
            content = new Content().value(attachment.content.value)
            xmEntity = destinationEntity
        }
        if (attachment.getTypeKey().equals("RECEIVERS")) {
            def originEntity = attachment.getXmEntity();
            destinationEntity.data.receiversCount = originEntity.data.receiversCount
            entityService.save(destinationEntity)
        }

        return copiedAttachment;
    }
}

