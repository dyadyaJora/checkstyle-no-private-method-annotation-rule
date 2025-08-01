package dev.jora.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dyadyaJora on 01.08.2025
 */
public class NoPrivateAnnotatedMethodCheck extends AbstractCheck {
    public static final String MSG_KEY = "forbidden.annotation.on.private";

    private final Set<String> forbiddenAnnotations = new HashSet<>();

    /**
     * Sets the names of annotations that are forbidden on private methods.
     *
     * @param names the names of the forbidden annotations
     */
    public void setForbiddenAnnotations(final String... names) {
        if (names != null) {
            Collections.addAll(forbiddenAnnotations, names);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST methodDef) {
        if (!isPrivate(methodDef)) {
            return;
        }

        DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers == null) return;

        for (DetailAST child = modifiers.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                String annName = extractAnnotationName(child);
                if (annName != null && isForbidden(annName)) {
                    log(child, MSG_KEY, annName);
                }
            }
        }
    }

    private boolean isPrivate(DetailAST methodDef) {
        DetailAST modifiers = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        return modifiers != null && modifiers.branchContains(TokenTypes.LITERAL_PRIVATE);
    }

    private boolean isForbidden(String annName) {
        return forbiddenAnnotations.contains(annName)
                || forbiddenAnnotations.contains("@" + annName);
    }

    private String extractAnnotationName(DetailAST annotationAst) {
        DetailAST ident = annotationAst.findFirstToken(TokenTypes.IDENT);
        if (ident != null) {
            return ident.getText();
        }

        DetailAST dot = annotationAst.findFirstToken(TokenTypes.DOT);
        if (dot != null) {
            return buildQualifiedName(dot);
        }

        return null;
    }

    private String buildQualifiedName(DetailAST node) {
        StringBuilder sb = new StringBuilder();
        recursiveBuildName(node, sb);
        return sb.toString();
    }

    private void recursiveBuildName(DetailAST node, StringBuilder sb) {
        if (node.getType() == TokenTypes.DOT) {
            recursiveBuildName(node.getFirstChild(), sb);
            sb.append('.').append(node.getLastChild().getText());
        } else {
            sb.append(node.getText());
        }
    }
}
