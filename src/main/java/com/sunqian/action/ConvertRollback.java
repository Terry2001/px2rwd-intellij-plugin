package com.sunqian.action;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import com.sunqian.utils.LogicUtils;
import com.sunqian.utils.StringUtils;

import java.util.Objects;
import java.util.Optional;

import static com.sunqian.constvalue.MagicValue.*;

/**
 * 转换回退action
 *
 * @author sunqian
 * date 2019/6/25
 */
public class ConvertRollback extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);

        NotificationGroup px2remInfo = new NotificationGroup("px2rem info", NotificationDisplayType.NONE, true);
        Notification notification = new Notification(
                px2remInfo.getDisplayId(),
                "psiFile Language id:",
                psiFile.getLanguage().getID(),
                NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);

        LogicUtils.getLogic().conOrEnd(
                psiFile,
                file -> Objects.nonNull(file)
                        && StringUtils.containsAny(
                        file.getLanguage().getID(),
                        STYLE_SHEET_LANGUAGE_ID,
                        LESS_LANGUAGE_ID,
                        SASS_LANGUAGE_ID,
                        HTML_LANGUAGE_ID,
                        SCSS_LANGUAGE_ID,
                        VUE_LANGUAGE_ID,
                        STYLUS_LANGUAGE_ID),
                file -> Optional.ofNullable(
                        ActionPerformer.getActionPerformer(
                                anActionEvent.getRequiredData(CommonDataKeys.PROJECT),
                                anActionEvent.getRequiredData(CommonDataKeys.EDITOR)))
                        .ifPresent(
                                ap -> Optional.of(FormatTools.getFormatTools(ap.getConstValue()))
                                        .ifPresent(
                                                formatTools -> ap.getCaretModel().runForEachCaret(
                                                        caret -> formatTools.rollbackStyle(
                                                                ap,
                                                                ap.getDocument().getLineNumber(caret.getOffset()))
                                                )
                                        )
                        )
        );
    }

}
