package com.sunqian.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.sunqian.model.ActionPerformer;
import com.sunqian.utils.FormatTools;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sunqian.constvalue.MagicValue.STYLE_PATTERN_FORMAT;
import static com.sunqian.constvalue.MagicValue.STYLE_SHEET_LANGUAGE_ID;

/**
 * intention action类
 *
 * @author sunqian
 * @date 2019/6/21
 */
public class PX2RWDIntention extends PsiElementBaseIntentionAction implements IntentionAction {
    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        Optional.ofNullable(ActionPerformer.getActionPerformer(project, editor)).ifPresent(ap ->
            Optional.of(FormatTools.getFormatTools(ap.getConstValue())).ifPresent(formatTools ->
                formatTools.formatLineCode(ap)
            )
        );
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        return Optional.of(element.getLanguage()).filter(language -> Objects.equals(language.getID(), STYLE_SHEET_LANGUAGE_ID)).map(language -> Optional.of(editor.getDocument().getLineNumber(editor.getCaretModel().getOffset())).map(lineNum ->
            Optional.of(editor.getDocument().getText(
                    new TextRange(editor.getDocument().getLineStartOffset(lineNum), editor.getDocument().getLineEndOffset(lineNum))
            )).map(text -> Pattern.compile(STYLE_PATTERN_FORMAT).matcher(text.toLowerCase()).matches()).get()
        ).orElse(false)).orElse(false);
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "PX2RWD converter";
    }

    @NotNull
    @Override
    public String getText() {
        return "PX2RWD";
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
