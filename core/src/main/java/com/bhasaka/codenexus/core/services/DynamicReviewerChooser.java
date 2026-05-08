package com.bhasaka.codenexus.core.services;

import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = ParticipantStepChooser.class,
        property = {
                "chooser.label=Codenexus Reviewer Chooser"
        }
)
public class DynamicReviewerChooser implements ParticipantStepChooser {

    private static final Logger log = LoggerFactory.getLogger(DynamicReviewerChooser.class);

    private static final String REVIEW_GROUP = "codenexus-review";
    private static final String ADMIN_GROUP = "codenexus-admin";

    @Override
    public String getParticipant(WorkItem workItem,
                                 WorkflowSession workflowSession,
                                 MetaDataMap metaDataMap) {

        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        log.info("Payload received: {}", payloadPath);

        if (payloadPath != null && payloadPath.contains("/content/dam/codenexus")) {
            return REVIEW_GROUP;
        }

        return ADMIN_GROUP;
    }
}