//
//  BotsRequestJSExport.h
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/2.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JavaScriptCore.h>

NS_ASSUME_NONNULL_BEGIN

@protocol HCRequestJSExport <JSExport>
JSExportAs(getRequest,
           - (void)requestGet:(NSString *)url params:(NSDictionary *)params success:(JSValue *)success fail:(JSValue *)fail
           );
@end

NS_ASSUME_NONNULL_END
