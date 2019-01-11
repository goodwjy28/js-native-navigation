//
//  BotsUIJSExport.h
//  BotsItoa
//
//  Created by 刘海川 on 2018/12/20.
//  Copyright © 2018 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JavaScriptCore.h>
NS_ASSUME_NONNULL_BEGIN

@protocol HCUIJSExport <JSExport>
JSExportAs(alert,
           - (void)alert:(NSString *)title message:(NSString *)msg affirm:(JSValue *)affirm cancel:(JSValue *)cancel
           );

@end

NS_ASSUME_NONNULL_END
