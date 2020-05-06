/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.partiql.pig.generator.kotlin

import org.partiql.pig.domain.model.TypeDomain

/*
  Note a big design consideration for the classes in this file is that they are easy to consume by the
  Apache FreeMarker template.  FreeMarker, like all template languages, is not great at expressing complex
  logic so we pre-compute most of the complicated aspects of generating the Kotlin code and populate the
  results to an instance of this domain model.  This helps keep the template much simpler than it would
  otherwise be.
 */

data class KTypeUniverse(val domains: List<KTypeDomain>)

data class KTypeDomain(
    val name: String,
    val tuples: List<KTuple>,
    val sums: List<KSum>
)

data class KProperty(
    val name: String,
    val type: String,
    val isVariadic: Boolean,
    val isNullable: Boolean,
    val transformExpr: String
)

data class KParameter(
    val name: String,
    val type: String,
    val defaultValue: String?,
    val isVariadic: Boolean
)

data class KConstructorArgument(
    val name: String,
    val value: String
)


data class KBuilderFunction(
    val name: String,
    val parameters: List<KParameter>,
    val constructorArguments: List<KConstructorArgument>
)

data class KTuple(
    val name: String,
    val constructorName: String,
    val superClass: String,
    val properties: List<KProperty>,
    val arity: IntRange,
    val builderFunctions: List<KBuilderFunction>,
    val isRecord: Boolean,
    val hasVariadicElement: Boolean
)

data class KSum(
    val name: String,
    val superClass: String,
    val variants: List<KTuple>
)

fun TypeDomain.toKTypeDomain(): KTypeDomain = KTypeDomainConverter(this).convert()

