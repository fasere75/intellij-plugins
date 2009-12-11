package com.intellij.tapestry.intellij.editorActions;

import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNamedElement;
import com.intellij.tapestry.TapestryBundle;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexey Chmutov
 *         Date: Dec 8, 2009
 *         Time: 3:35:11 PM
 */
public class TmlFindUsagesProvider implements FindUsagesProvider {
  public WordsScanner getWordsScanner() {
    return null;
  }

  public boolean canFindUsagesFor(@NotNull final PsiElement psiElement) {
    return psiElement instanceof PsiMethod || psiElement instanceof PsiField;
  }

  public String getHelpId(@NotNull final PsiElement psiElement) {
    return null;
  }

  @NotNull
  public String getType(@NotNull final PsiElement element) {
    return TapestryBundle.message("type.name.reference");
  }

  @NotNull
  public String getDescriptiveName(@NotNull final PsiElement element) {
    if (element instanceof PsiNamedElement) {
      final String name = ((PsiNamedElement)element).getName();
      if (name != null) {
        return name;
      }
    }
    return TapestryBundle.message("type.name.reference");
  }

  @NotNull
  public String getNodeText(@NotNull final PsiElement element, final boolean useFullName) {
    if (element instanceof PsiNamedElement) {
      return ((PsiNamedElement)element).getName();
    }
    return element.getText();
  }
}

