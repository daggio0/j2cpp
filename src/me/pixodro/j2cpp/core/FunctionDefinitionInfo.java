package me.pixodro.j2cpp.core;

import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTQualifiedName;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class FunctionDefinitionInfo extends AbstractFunctionInfo {
  private static final CPPNodeFactory f = CPPNodeFactory.getDefault();
  private final ICPPASTFunctionDefinition definition;

  public FunctionDefinitionInfo(final MethodDeclaration methodDeclaration, final TypeDeclaration typeDeclaration, final TypeDeclarationInfo enclosingType) {
    super(methodDeclaration, enclosingType);
    final ICPPASTQualifiedName qualifiedName = f.newQualifiedName();
    // for (String name : namespace) {
    // qualifiedName.addName(f.newName(name.toCharArray()));
    // }
    if (enclosingType != null) {
      qualifiedName.addName(new NameInfo(enclosingType.getName()).getName());
    }
    qualifiedName.addName(new NameInfo(typeDeclaration.getName()).getName());
    qualifiedName.addName(new NameInfo(methodDeclaration.getName()).getName());

    // Declarator use for function definition (cpp)
    functionDeclarator.setName(qualifiedName);

    // Method body
    final StatementInfo statementInfo = new StatementInfo(methodDeclaration.getBody(), typeDeclaration);
    definition = f.newFunctionDefinition(declSpecifier, functionDeclarator, statementInfo.getStatement());
    // if (methodDeclaration.isConstructor() && (enclosingType != null)) {
    // // If the type is nested, simulate Java inner class access visibility by
    // // creating a reference to the outer type on the constructor
    // final List<IASTInitializerClause> initializerClauses = new
    // ArrayList<IASTInitializerClause>();
    // initializerClauses.add(f.newIdExpression(f.newName("__parent".toCharArray())));
    // final ICPPASTConstructorInitializer initializer =
    // f.newConstructorInitializer(initializerClauses.toArray(new
    // IASTInitializerClause[initializerClauses.size()]));
    // final ICPPASTConstructorChainInitializer chainInitializer =
    // f.newConstructorChainInitializer(f.newName("__parent".toCharArray()), initializer);
    // definition.addMemberInitializer(chainInitializer);
    // }
  }

  public ICPPASTFunctionDefinition getDefinition() {
    return definition;
  }
}